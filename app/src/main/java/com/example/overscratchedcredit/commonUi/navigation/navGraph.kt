package com.example.overscratchedcredit.commonUi.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.overscratchedcredit.camera.presentation.screens.ScannerScreen
import com.example.overscratchedcredit.chooseMethods.presentation.screen.ChooseMethodScreen
import com.example.overscratchedcredit.homePage.presentation.screen.HomeScreen
import com.example.overscratchedcredit.manual_input.presentation.ManualEntryRoute
//import com.example.overscratchedcredit.manual_input.presentation.screen.ManualScreen
import com.example.overscratchedcredit.results.presentation.screens.ResultScreen
import com.example.overscratchedcredit.splashscreen.presentation.composable.SplashScreen
import kotlinx.coroutines.delay

@Composable
fun AppNavGraph() {

    val backStack = rememberNavBackStack(Splash)

    // NavDisplay watches the backStack and renders whatever destination is on top.
    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {

            // SPLASH

            entry<Splash> {
                LaunchedEffect(Unit) {
                    delay(2000)
                    // Clear the splash from the stack and start Home as the root
                    backStack.clear()
                    backStack.add(Home)
                }
                SplashScreen()
            }

            // HOME
            // "Recover a voucher" pushes ChooseMethod onto the stack.
            entry<Home> {
                HomeScreen(
                    onRecoverClick = { backStack.add(ChooseMethod) },
                    onHowItWorksClick = { /* TODO: Implement How it Works */ }
                )
            }

            // CHOOSE METHOD
            // Three options, each pushes a different destination.
            entry<ChooseMethod> {
                ChooseMethodScreen(
                    onScanClick = { backStack.add(Scanner) },
                    onManualClick = { backStack.add(ManualEntry) },
                    onZuriClick = { backStack.add(Zuri) }
                )
            }

            // SCANNER
            // Back button pops the stack (goes back to ChooseMethod).
            // Shutter will push PinResult with the recovered PIN.
            entry<Scanner> {
                ScannerScreen(
                    onBackClick = { backStack.removeLastOrNull() },
                    onManualEntryClick = { backStack.add(ManualEntry) },
                    onShutterClick = { 
                        // the PIN would come from the scanner logic
                        backStack.add(PinResult(pin = "1234567890123456")) 
                    }
                )
            }

            // RESULT
            // The lambda provides the instance of the key (PinResult), giving access to its properties.
            entry<PinResult> { result ->
                ResultScreen(
                    pin = result.pin,
                    amount = result.amount,
                    serialNumber = result.serial,
                    partialPin = result.partialPin,
                    onBackClick = { backStack.clear(); backStack.add(Home) }
                )
            }

            // STUBS — Placeholder UI for screens not yet built
            // MANUAL ENTRY
            entry<ManualEntry> {
                ManualEntryRoute(
                    onNavigateBack = { backStack.removeLastOrNull() },
                    onNavigateToResult = { pin, amount, serial, partialPin ->
                        backStack.add(PinResult(pin = pin, amount = amount, serial = serial, partialPin = partialPin))
                    }
                )
            }


            entry<Zuri> { 
                Text("Zuri - TODO") 
            }
        }
    )
}
