package tcs.app.dev.homework1

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import tcs.app.dev.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ShopTopAppBar(
    title: String,
    showCartIcon: Boolean,
    cartItemCount: Int,
    onCartClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
            }
        },
        actions = {
            if (showCartIcon) {
                IconButton(onClick = onCartClick) {
                    if (cartItemCount > 0) {
                        BadgedBox(badge = {
                            Badge { Text(if (cartItemCount > 99) stringResource(R.string.more_than_99) else cartItemCount.toString()) }
                        }) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = stringResource(R.string.title_cart)
                            )
                        }
                    }
                }
            }
        }
    )
}