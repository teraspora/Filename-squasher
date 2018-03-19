(defproject file-renamer "0.1.0"
  :description "Replaces spaces in names of all directories and files in the given directory with the given character"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :main ^:skip-aot file-renamer.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
