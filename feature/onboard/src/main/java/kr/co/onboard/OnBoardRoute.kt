package kr.co.onboard

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kr.co.domain.model.AuthType
import kr.co.onboard.login.Login
import kr.co.ui.ext.scaffoldBackground

@Composable
internal fun OnBoardRoute(
    viewModel: OnBoardViewModel = hiltViewModel(),
    navController: NavController
) {
   OnBoardScreen(
       onSocialLoginClick = viewModel::onSocialLoginClick,
       navController = navController
   )
}

@Composable
private fun OnBoardScreen(
    onSocialLoginClick: (AuthType) -> Unit,
    navController: NavController
) {
    Scaffold(
        topBar = {

        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .scaffoldBackground(
                    scaffoldPadding = scaffoldPadding
                ),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Login(
                onSocialLoginClick = onSocialLoginClick,
                navController = navController
            )
        }
    }
}
