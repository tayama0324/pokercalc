name := "pokercalc"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"

fork in (Test, run) := true

javaOptions += "-agentlib:hprof=cpu=times,file=/tmp/stat.txt"

enablePlugins(JavaAppPackaging)

scalacOptions += "-optimize"
