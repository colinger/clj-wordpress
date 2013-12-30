(ns clj-wordpress.post
  (:use [clj-wordpress.core]
        [cheshire.core]))

(def config {:host "http://picgather.com"
             :blog-id 0
             :username ""
             :password "" })
;                                        
(defn- xml-str
 "Like clojure.core/str but escapes < > and &."
 [x]
  (-> x str (.replace "&" "&amp;") (.replace "<" "&lt;") (.replace ">" "&gt;")))
;
(defn get-post 
  "retrieve a post with id"
  [config id]
  (with-wp (merge config {:blog-id id}) (wp 'getPost nil)))
;
(defn new-post
  "create a new post: title, content, tags"
  ([name pwd title content tags]
     (let [config (merge config {:username name :password pwd})
           title (if (or (empty? title) (nil? title)) "." title)
           result (generate-string (with-wp (merge config {:publish true}) (wp 'newPost [{:title title :description (xml-str content) :mt_keywords tags}])))]
       ;;(println "result -> " result)
       (re-find #"[0-9]+" result)))
  ([title content tags]
     (let [title (if (or (empty? title) (nil? title)) "." title)
           result (generate-string (with-wp (merge config {:publish true}) (wp 'newPost [{:title title :description (xml-str content) :mt_keywords tags}])))]
       ;;(println "result -> " result)
       (re-find #"[0-9]+" result))))
