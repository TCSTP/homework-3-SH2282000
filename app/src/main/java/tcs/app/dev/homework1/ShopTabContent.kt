package tcs.app.dev.homework1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import tcs.app.dev.R
import tcs.app.dev.homework1.data.Cart
import tcs.app.dev.homework1.data.Item
import tcs.app.dev.homework1.data.MockData
import tcs.app.dev.homework1.data.Shop

@Composable
fun ShopTabContent(
    shop: Shop,
    cart: Cart,
    onAdd: (Item) -> Unit,
    modifier: Modifier = Modifier.Companion
) {
    val itemsList = shop.items.toList()

    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(itemsList) { item ->
            val price = shop.prices[item] ?: return@items

            // Get name and image from MockData
            val itemName = stringResource(id = MockData.getName(item)) // resolved string
            val itemImage = MockData.getImage(item)

            Row(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Image(
                        painter = painterResource(id = itemImage),
                        contentDescription = itemName,
                        modifier = Modifier.Companion.size(48.dp)
                    )
                    Spacer(modifier = Modifier.Companion.width(8.dp))
                    Column {
                        Text(itemName)
                        Text(price.toString())

                    }
                }

                Button(onClick = { onAdd(item) }) {
                    Text(stringResource(R.string.description_add_to_cart))
                }
            }
        }
    }
}