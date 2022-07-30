package com.sgcdeveloper.runwork.core

import kotlinx.coroutines.Dispatchers

class DefaultDispatchersProvider : DispatchersProvider {
    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
    override val default = Dispatchers.Default
    override val unconfined = Dispatchers.Unconfined
}