/**
 * SugarExt
 * Functions to make code look better.
 */
package cartography.util

/* ------------------------------ ------------------------------ */
// Function ?contracts?
/* ------------------------------ ------------------------------ */

/**
 * Extension function that invokes other function
 * only if given condition is true.
 */
fun contract(condition: Boolean, call: () -> Unit) {
    if (!condition)
        return

    run(call)
}

fun Boolean.expect(value: Boolean = true, call: () -> Unit) = contract(this == value, call)

/* ------------------------------ ------------------------------ */
// ?? ? ? ? ? ?? ? ? ? ? ? ???
/* ------------------------------ ------------------------------ */

/**
 * Function to check if all given values
 * are not null.
 **/
fun not_null(vararg values: Any?): Boolean {
    return values.count { element -> element == null } == 0
}