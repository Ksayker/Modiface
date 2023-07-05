package com.ksayker.modiface.presentation.base

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : BaseState, Event : BaseEvent, Label : BaseLabel>(state: State) : ViewModel(),
    DefaultLifecycleObserver {

    private val _state = MutableStateFlow(state)
    val state: StateFlow<State> get() = _state.asStateFlow()

    private val _label = MutableSharedFlow<Label>()
    val label: SharedFlow<Label> get() = _label.asSharedFlow()

    abstract fun onEvent(event: Event)

    var currentState: State = state
        private set

    protected fun updateState(newState: State) {
        currentState = newState
        _state.value = newState
    }

    protected suspend fun publishLabel(label: Label) {
        _label.emit(label)
    }

    protected fun launch(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }
}