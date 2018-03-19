# Filename-squasher

* Do you have loads of music CDs on your hard disk, where the file and directory names have been automatically generated from online databases?

* Have the filenames got spaces between the words?

* Do you find this annoying when you have to quote them on the command line, for example?

### If [:yes :yes :yes]...
Use this short program to replace whitespace in names of all directories and files in the given directory with a different separator.

## Usage

REPL - `(require 'file-renamer.core)
       (-main <base directory <new separator string>)`

Shell/Leiningen - `lein run <base directory> <new separator string>`

## Examples

`lein run "/home/fred/test/" ""`

`lein run "/music/artists/" "-"`

## License

Copyright © 2017 John Lynch

Distributed under the Eclipse Public License either version 1.0 or whatever.