package tcs.app.dev.homework1

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import tcs.app.dev.R

@Composable
fun ShopBottomBar(
    selectedTab: ShopTab,
    onTabSelected: (ShopTab) -> Unit,
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedTab == ShopTab.Shop,
            onClick = { onTabSelected(ShopTab.Shop) },
            label = { Text(stringResource(R.string.label_shop)) },
            icon = { }
        )

        NavigationBarItem(
            selected = selectedTab == ShopTab.Discounts,
            onClick = { onTabSelected(ShopTab.Discounts) },
            label = { Text(stringResource(R.string.label_discounts)) },
            icon = { }
        )
    }
}