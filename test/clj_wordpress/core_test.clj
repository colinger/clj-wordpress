(ns clj-wordpress.core-test
  (:require [clojure.test :refer :all]
            [clj-wordpress.core :refer :all]
            )
  (use [clj-wordpress.test-config :only [config]]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))
