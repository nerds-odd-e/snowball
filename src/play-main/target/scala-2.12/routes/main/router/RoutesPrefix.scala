// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/csd/workspace/massive_mailer/src/play-main/conf/routes
// @DATE:Tue May 14 15:34:30 JST 2019


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
