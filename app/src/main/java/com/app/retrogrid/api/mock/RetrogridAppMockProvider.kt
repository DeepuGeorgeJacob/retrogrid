package com.app.retrogrid.api.mock

import com.app.retrogrid.mock.MockProvider

class RetrogridAppMockProvider : MockProvider {
    override val isEnabled: Boolean
        get() = true

}