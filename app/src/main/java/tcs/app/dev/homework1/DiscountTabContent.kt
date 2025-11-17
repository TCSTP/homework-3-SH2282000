package tcs.app.dev.homework1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import tcs.app.dev.R
import tcs.app.dev.homework1.data.Cart
import tcs.app.dev.homework1.data.Discount
import tcs.app.dev.homework1.data.MockData

@Composable
fun DiscountTabContent(
    discounts: List<Discount>,
    cart: Cart,
    onAddDiscount: (Discount) -> Unit,
    modifier: Modifier = Modifier.Companion
) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(discounts) { discount ->

            // Generate a display string depending on discount type
            val discountText = when (discount) {
                is Discount.Percentage -> "${discount.value}% off"
                is Discount.Fixed -> "€${discount.amount.cents / 100u}.${discount.amount.cents % 100u} off"
                is Discount.Bundle -> {
                    val itemName = MockData.getName(discount.item)
                    "${discount.amountItemsPay} for ${discount.amountItemsGet} $itemName"
                }
            }

            Row(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(discountText)

                val alreadyInCart = cart.discounts.contains(discount)
                Button(
                    onClick = { onAddDiscount(discount) },
                    enabled = !alreadyInCart
                ) {
                    Text(stringResource(R.string.description_add_to_cart)) // simplest working text
                }
            }
        }
    }
}