(ns clj-wordpress.post
  (:use clj-wordpress.core))

(def config {:host "http://picgather.com"
  :blog-id 0
  :username "colin"
  :password "xinying_ge" })
;
(defn get-post 
  ""
  [id]
  (with-wp config (wp 'getPost [id])))
;
(defn new-post
  ""
  [config title content tags]
  (let [title (if (or (empty? title) (nil? title)) "." title)]
    (with-wp config (wp 'newPost [{:post_title title :post_content content :mt_keywords tags}])))
