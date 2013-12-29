(ns clj-wordpress.post
  (:use [clj-wordpress.core]
        [cheshire.core]))

(def config {:host "http://picgather.com"
  :blog-id 0
  :username "colin"
  :password "xinying_ge" })
;
(defn get-post 
  "retrieve a post with id"
  [config id]
  (with-wp (merge config {:blog-id id}) (wp 'getPost nil)))
;
(defn new-post
  "create a new post: title, content, tags"
  [config title content tags]
  (let [title (if (or (empty? title) (nil? title)) "." title)]
    (re-find #"[0-9]+" 
             (generate-string (with-wp (merge config {:publish true}) (wp 'newPost [{:title title :description content :mt_keywords tags}]))))))
