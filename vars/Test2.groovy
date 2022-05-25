#!groovy

def test2(build) {
    def Test = new Test()
    return Test.test(build)
}

return this
