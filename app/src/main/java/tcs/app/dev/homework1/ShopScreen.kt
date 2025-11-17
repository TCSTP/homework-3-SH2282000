package tcs.app.dev.homework1


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import tcs.app.dev.R
import tcs.app.dev.homework1.data.Cart
import tcs.app.dev.homework1.data.Discount
import tcs.app.dev.homework1.data.Shop
import tcs.app.dev.homework1.data.minus
import tcs.app.dev.homework1.data.plus
import tcs.app.dev.homework1.data.update

/**
 * # Homework 3 — Shop App
 *
 * Build a small shopping UI with ComposeUI using the **example data** from the
 * `tcs.app.dev.homework.data` package (items, prices, discounts, and ui resources).
 * The goal is to implement three tabs: **Shop**, **Discounts**, and **Cart**.
 *
 * ## Entry point
 *
 * The composable function [ShopScreen] is your entry point that holds the UI state
 * (selected tab and the current `Cart`).
 *
 * ## Data
 *
 * - Use the provided **example data** and data types from the `data` package:
 *   - `Shop`, `Item`, `Discount`, `Cart`, and `Euro`.
 *   - There are useful resources in `res/drawable` and `res/values/strings.xml`.
 *     You can add additional ones.
 *     Do **not** hard-code strings in the UI!
 *
 * ## Requirements
 *
 * 1) **Shop item tab**
 *    - Show all items offered by the shop, each row displaying:
 *      - item image + name,
 *      - item price,
 *      - an *Add to cart* button.
 *    - Tapping *Add to cart* increases the count of that item in the cart by 1.
 *
 * 2) **Discount tab**
 *    - Show all available discounts with:
 *      - an icon + text describing the discount,
 *      - an *Add to cart* button.
 *    - **Constraint:** each discount can be added **at most once**.
 *      Disable the button (or ignore clicks) for discounts already in the cart.
 *
 * 3) **Cart tab**
 *    - Only show the **Cart** tab contents if the cart is **not empty**. Within the cart:
 *      - List each cart item with:
 *        - image + name,
 *        - per-row total (`price * amount`),
 *        - an amount selector to **increase/decrease** the quantity (min 0, sensible max like 99).
 *      - Show all selected discounts with a way to **remove** them from the cart.
 *      - At the bottom, show:
 *        - the **total price** of the cart (items minus discounts),
 *        - a **Pay** button that is enabled only when there is at least one item in the cart.
 *      - When **Pay** is pressed, **simulate payment** by clearing the cart and returning to the
 *        **Shop** tab.
 *
 * ## Navigation
 * - **Top bar**:
 *      - Title shows either the shop name or "Cart".
 *      - When not in Cart, show a cart icon.
 *        If you feel fancy you can add a badge to the icon showing the total count (capped e.g. at "99+").
 *      - The cart button is enabled only if the cart contains items. In the Cart screen, show a back
 *        button to return to the shop.
 *
 * - **Bottom bar**:
 *       - In Shop/Discounts, show a 2-tab bottom bar to switch between **Shop** and **Discounts**.
 *       - In Cart, hide the tab bar and instead show the cart bottom bar with the total and **Pay**
 *         action as described above.
 *
 * ## Hints
 * - Keep your cart as a single source of truth and derive counts/price from it.
 *   Rendering each list can be done with a `LazyColumn` and stable keys (`item.id`, discount identity).
 * - Provide small reusable row components for items, cart rows, and discount rows.
 *   This keeps the screen implementation compact.
 *
 * ## Bonus (optional)
 * Make the app feel polished with simple animations, such as:
 * - `AnimatedVisibility` for showing/hiding the cart,
 * - `animateContentSize()` on rows when amounts change,
 * - transitions when switching tabs or updating the cart badge.
 *
 * These can help if want you make the app feel polished:
 * - [NavigationBar](https://developer.android.com/develop/ui/compose/components/navigation-bar)
 * - [Card](https://developer.android.com/develop/ui/compose/components/card)
 * - [Swipe to dismiss](https://developer.android.com/develop/ui/compose/touch-input/user-interactions/swipe-to-dismiss)
 * - [App bars](https://developer.android.com/develop/ui/compose/components/app-bars#top-bar)
 * - [Pager](https://developer.android.com/develop/ui/compose/layouts/pager)
 *
 */

enum class ShopTab { Shop, Discounts, Cart }

@Composable
fun ShopScreen(
    shop: Shop,
    availableDiscounts: List<Discount>,
    modifier: Modifier = Modifier
) {
    var cart by rememberSaveable { mutableStateOf(Cart(shop = shop)) }
    var selectedTab by rememberSaveable { mutableStateOf(ShopTab.Shop) }

    val isCartEmpty = cart.items.isEmpty() && cart.discounts.isEmpty()

    Scaffold(
        modifier = modifier,
        topBar = {
            ShopTopAppBar(
                title = if (selectedTab == ShopTab.Cart) stringResource(R.string.title_cart) else stringResource(
                    R.string.name_shop
                ),
                showCartIcon = selectedTab != ShopTab.Cart,
                cartItemCount = cart.totalCount.toInt(),
                onCartClick = { if (!isCartEmpty) selectedTab = ShopTab.Cart },
                onBackClick = { if (selectedTab == ShopTab.Cart) selectedTab = ShopTab.Shop }
            )
        },
        bottomBar = {
            if (selectedTab != ShopTab.Cart) {
                ShopBottomBar(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            } else {
                CartBottomBar(
                    totalPrice = cart.price,
                    enablePay = !isCartEmpty,
                    onPay = {
                        cart = Cart(shop = shop)
                        selectedTab = ShopTab.Shop
                    }
                )
            }
        }
    ) { innerPadding ->
        // Use the padding on the container of your tab content
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                ShopTab.Shop -> ShopTabContent(
                    shop = shop,
                    cart = cart,
                    onAdd = { item -> cart += item },
                    modifier = Modifier.fillMaxSize() // content now respects scaffold padding
                )

                ShopTab.Discounts -> DiscountTabContent(
                    discounts = availableDiscounts,
                    cart = cart,
                    onAddDiscount = { d -> cart += d },
                    modifier = Modifier.fillMaxSize()
                )

                ShopTab.Cart -> CartTabContent(
                    cart = cart,
                    onUpdateItemAmount = { item, amt -> cart = cart.update(item to amt.toUInt()) },
                    onRemoveDiscount = { d -> cart = cart - d },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

