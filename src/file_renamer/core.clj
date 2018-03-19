;;;; Program:  filename-squasher, vsn 1.00
;;;; Author:  John Lynch
;;;; Date:  March 2018
;;;; Use:  Replaces whitespace in names of all directories and files in the given directory with the given separator.

(ns file-renamer.core
  (:gen-class)
  (:require [clojure.string :as str]
  			[clojure.pprint :as pp]
			[clojure.java.io :refer :all]))

(import '[java.nio.file])

(defn traverse-dir 
	"Return a seq of pathnames of directories and files in the given directory"
	[dir-path]
    (map #(.getAbsolutePath %) (file-seq (file dir-path))))

(defn replace-spaces
    "Return a copy of string s1 with all sequences of spaces replaced with string s2"
    [s1 s2]
    (str/replace s1 #"\s+" s2))

(defn despace-filenames
    "Map a fn to rename a file (by replacing all substrings of whitespace in the name with the given separator) 
    over all files and dirs in the given directory"
    [direc separator]
    (pp/pprint "Getting list of directories...")

    ; need to rename directories deepest first to obviate problems with changed references, so sort them;
    (let [dir-list 	  (filter #(.isDirectory (as-file %)) (reverse (sort-by  #(-> % as-file .toPath .getNameCount) (vec (traverse-dir direc)))))]
	  (pp/pprint "Renaming directories...")
      (doseq [dir dir-list] 
  		(let [d  (as-file dir)
	  		  dname (.getName d)]
	  	  (when (str/includes? dname " ") (.renameTo d (java.io.File. (.getParent d) (replace-spaces dname separator)))))))

    ; now get an updated file list, then rename files; order unimportant
    (let [file-list       (filter #(.isFile (as-file %)) (vec (traverse-dir direc)))
  	      new-file-list   (vec (map #(replace-spaces % separator) file-list))]
  	  (pp/pprint "Renaming files...")
      (doseq [[f1 f2] (map list file-list new-file-list)] (when-not (= f1 f2) (.renameTo (as-file f1) (as-file f2))))))

(defn -main
    [direc separator]
	(despace-filenames direc separator)
	(pp/pprint "Done!"))