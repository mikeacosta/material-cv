package net.acosta.mike.resume.utils

import java.util.concurrent.Executor

class CustomExecutor : Executor {
    override fun execute(runnable: Runnable) {
        Thread(runnable).start()
    }
}