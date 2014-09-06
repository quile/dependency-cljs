(defproject com.stuartsierra/dependency "0.1.2-SNAPSHOT"
  :description "A data structure for representing dependency graphs"
  :url "https://github.com/stuartsierra/dependency"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :cljx {:builds [{:source-paths ["src/cljx"]
                   :output-path "target/classes"
                   :rules :clj}

                  {:source-paths ["src/cljx"]
                   :output-path "target/classes"
                   :rules :cljs}

                  {:source-paths ["test/cljx"]
                   :output-path "target/test-classes"
                   :rules :clj}

                  {:source-paths ["test/cljx"]
                   :output-path "target/test-classes"
                   :rules :cljs}]}

  :cljsbuild {:test-commands {"node" ["node" :node-runner "target/testable.js"]}
              :builds [{:source-paths ["target/classes" "target/test-classes"]
                        :compiler {:output-to "target/testable.js"
                                   :optimizations :simple
                                   :target :nodejs
                                   :pretty-print true}}]}

  :profiles {:dev {:hooks        [cljx.hooks]
                   :test-paths   ["target/test-classes"]
                   :dependencies [[org.clojure/clojure "1.6.0"]
                                  [org.clojure/clojurescript "0.0-2311"]]
                   :plugins      [[com.keminglabs/cljx "0.4.0" :exclusions [org.clojure/clojure]]
                                  [com.cemerick/clojurescript.test "0.3.1"]
                                  [lein-cljsbuild "1.0.4-SNAPSHOT"]]}
             :clj-1.4.0 {:dependencies [[org.clojure/clojure "1.4.0"]]}
             :clj-1.3.0 {:dependencies [[org.clojure/clojure "1.3.0"]]}})
