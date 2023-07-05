package com.ksayker.modiface.presentation.utils

import androidx.fragment.app.Fragment
import com.ksayker.modiface.presentation.base.BaseState
import kotlin.properties.ReadOnlyProperty

inline fun <reified T : BaseState> stateArgs(key: String): ReadOnlyProperty<Fragment, T> {
    return ReadOnlyProperty { thisRef, _ ->
        val args = thisRef.requireArguments()
        require(args.containsKey(key)) { "Arguments don't contain key '$key'" }
        requireNotNull(
            if (atLeastTiramisu()) {
                args.getParcelable(key, T::class.java)
            } else {
                args.getParcelable(key)
            }
        )
    }
}
