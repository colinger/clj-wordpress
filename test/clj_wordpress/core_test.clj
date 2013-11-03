(ns clj-wordpress.core-test
  (:require [clojure.test :refer :all]
            [clj-wordpress.core :refer :all]
            )
  (use [clj-wordpress.test-config :only [config]]))

(deftest configuration
  (testing "Configuration"
    (is (= true 
          (reduce 
           (fn [x y] (and x y))
           (map (fn [x]
                  (contains? config x))
                [:host :blog-id :username :password]))))))

(deftest prepare-params-empty
  (testing "Preparing parameters with no additional param"
    (is (= 3 (count (prepare-params config nil))))))

(deftest prepare-params-one
  (testing "preparing parameters with one additional param"
    (is (= "foo" (last (prepare-params config ["foo"]))))))

(deftest prepare-params-multiple
  (testing "preparing with multiple parameters"
    (is (= 6 (count (prepare-params config [1 2 3]))))))

(deftest assemble-request-with-empty-params
  (testing "assemble-request empty parameters"
    (is (= "<?xml version='1.0' encoding='UTF-8'?>\n<methodCall>\n<methodName>\ntest\n</methodName>\n<params>\n</params>\n</methodCall>\n" (assemble-request "test" nil)))))

(deftest parse-xml-test
  (testing "parse-xml"
    (is (= [{:tag :a, :attrs nil, :content ["test"]} nil]
           (parse-xml "<a>test</a>")))))

(deftest beautify-xml-test
  (testing "beautify-xml"
    (is (= {:text "test"}
           (:a 
            (beautify-xml "<a>test</a>"))))))