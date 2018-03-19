;;;; Program:  filename-squasher, vsn 1.00
;;;; Author:  John Lynch
;;;; Date:  March 2018
;;;; Use:  Replaces whitespace in names of all directories and files in the given directory with the given separator.

(ns file-renamer.core
  (:gen-class)
  (:require [clojure.string :as str]))

(import '[java.nio.file])

(use '[clojure.java.io])
(use '[clojure.pprint])

(defn traverse-dir 
	"Return a seq of pathnames of directories and files in the given directory"
	[dir-path]
    (map #(.getAbsolutePath %) (file-seq (file dir-path))))

(defn replace-spaces
    "Return a copy of string s1 with all sequences of spaces replaced with string s2"
    [s1 s2]
    (str/replace s1 #"\s+" s2))

(defn despace-filenames
    "Map a fn to rename a file over all files and dirs in the given directory"
    [direc separator]
    ; need to rename directories deepest first, so sort them;
    (let [folist      (reverse (sort-by  #(-> % as-file .toPath .getNameCount) (vec (traverse-dir direc))))
    	  dlist 	  (filter #(.isDirectory (as-file %)) folist)]
  	  ;(pprint dlist)
  	  (pprint "Renaming directories...")
      (doseq [dir dlist] 
      		(let [d (as-file dir)
      			  dn (.getName d)
      			  p (.getParent d)]
      			  (when (str/includes? dn " ") (.renameTo d (as-file (java.io.File. p (replace-spaces dn separator)))))                   )))

    ; now rename files; order unimportant
    (let [flist       (filter #(.isFile (as-file %)) (vec (traverse-dir direc)))
  	      new-flist   (vec (map #(replace-spaces % separator) flist))]
  	  ; (pprint flist)
     ;  (pprint new-flist)
      (pprint "Renaming files...")
      (doseq [[f1 f2] (map list flist new-flist)] (when-not (= f1 f2) (.renameTo (as-file f1) (as-file f2))))))

(defn -main
    [& [direc separator]]
	(despace-filenames direc separator)
	(pprint "Done!"))

