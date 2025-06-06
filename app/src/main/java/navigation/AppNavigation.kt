package navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import screens.DashboardScreen
import screens.GuestRegistrationScreen
import screens.AddRoomScreen
import screens.ParqueaderoRegisterScreen
import screens.RegistrarReservaScreen
import screens.ReservaListScreen
import screens.ViewRoomsScreen
import viewmodel.HuespedViewModel
import viewmodel.ParqueaderoViewModel
import viewmodel.ReservaViewModel
import viewmodel.RoomViewModel

sealed class Routes(val route: String) {
    object Dashboard : Routes("dashboard")
    object RegisterGuest : Routes("register_guest")
    object AddRoom : Routes("Add_Room")
    object ViewRoom : Routes("View_Room")
    object AddReserva : Routes("Add_Reserva")
    object ViewReserva : Routes("View_Reserva")
    object ParkingAdd : Routes("Parking_Add")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    val huespedViewModel: HuespedViewModel = viewModel()
    val roomViewModel: RoomViewModel = viewModel() // Esta es la instancia correcta
    val reservaViewModel : ReservaViewModel = viewModel()
    val parqueaderoViewModel : ParqueaderoViewModel = viewModel()

    NavHost(navController = navController, startDestination = Routes.Dashboard.route) {
        composable(Routes.Dashboard.route) {
            DashboardScreen(navController)
        }
        composable(Routes.RegisterGuest.route) {
            GuestRegistrationScreen(huespedViewModel, navController)
        }
        composable(Routes.AddRoom.route) {
            AddRoomScreen(roomViewModel, navController) // Aquí pasas la instancia
        }
        composable(Routes.ViewRoom.route) {
            ViewRoomsScreen(roomViewModel, navController)
        }
        composable(Routes.AddReserva.route) {
            RegistrarReservaScreen(reservaViewModel, navController)
        }
        composable(Routes.ViewReserva.route) {
            ReservaListScreen(reservaViewModel, navController)
        }
        composable(Routes.ParkingAdd.route) {
            ParqueaderoRegisterScreen(parqueaderoViewModel, navController)
        }
    }
}