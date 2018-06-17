/**
 * ErrorExt
 * Extension functions for error handling.
 */
package cartography.util

import com.runemate.game.api.client.ClientUI

/* ------------------------------ ------------------------------ */
// Extensions for exception control.
/* ------------------------------ ------------------------------ */

@Suppress("FunctionName")
fun <R : Any?> CATCH(body: () -> R?): R? = CATCH(body, false)

@Suppress("FunctionName")
fun <R : Any?> CATCH_STRICT(body: () -> R?): R? = CATCH(body, true)

@Suppress("FunctionName")
fun <R : Any?> CATCH(body: () -> R?, critical: Boolean = false): R? {
    try { return body.invoke() } catch (e: Exception) { e.handle(critical) }
    return null
}

/* ------------------------------ ------------------------------ */
// Extensions for exception error reporting.
/* ------------------------------ ------------------------------ */

fun Exception.handle(critical: Boolean) = when(critical)
{
    true ->
    {
        ClientUI.showAlert("Exception occurred with termination request. Check logs.")
        ERROR(localizedMessage)
        EXIT("Exception occurred with termination request.")
    }
    false -> { ERROR(localizedMessage) }
}