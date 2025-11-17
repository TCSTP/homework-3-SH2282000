package tcs.app.dev.homework1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import tcs.app.dev.R
import tcs.app.dev.homework1.data.Cart
import tcs.app.dev.homework1.data.Discount
import tcs.app.dev.homework1.data.Item

@Composable
fun CartTabContent(
    cart: Cart,
    onUpdateItemAmount: (Item, Int) -> Unit,
    onRemoveDiscount: (Discount) -> Unit,
    modifier: Modifier = Modifier.Companion
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item { Text(stringResource(R.string.items), style = MaterialTheme.typography.titleLarge) }

        val entries = cart.items.entries.toList()
        items(entries) { (item, amount) ->
            Row(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                Text("${item.id} (x$amount)")
                Row {
                    Button(
                        onClick = { onUpdateItemAmount(item, amount.toInt() - 1) },
                        enabled = amount > 1u
                    ) { Text("-") }
                    Spacer(Modifier.Companion.width(8.dp))
                    Button(onClick = { onUpdateItemAmount(item, amount.toInt() + 1) }) { Text("+") }
                }
            }
        }

        item { Spacer(Modifier.Companion.height(16.dp)) }

        item {
            Text(
                stringResource(R.string.label_discounts),
                style = MaterialTheme.typography.titleLarge
            )
        }

        items(cart.discounts) { discount ->
            Row(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val displayText = when (discount) {
                    is Discount.Percentage -> "${discount.value}% off"
                    is Discount.Fixed -> "€${discount.amount.cents / 100u}.${discount.amount.cents % 100u} off"
                    is Discount.Bundle -> "${discount.item.id} ${discount.amountItemsPay} for ${discount.amountItemsGet}"
                }

                Text(displayText)
                Button(
                    onClick =
                        { onRemoveDiscount(discount) }) { Text(stringResource(R.string.description_remove_from_cart)) }
            }
        }
    }
}