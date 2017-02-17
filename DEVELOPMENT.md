File Photo Viewer - Development Notes
-------------------------------------

To update a new version to Bintray Jcenter and Maven, perform the following steps:
* In the file 'file-photoview/build.gradle', update the version property (libraryVersion)
* In the project root folder, run the following commands
    ```
    gradlew install
    gradlew bintrayUpload
    ```
* Go to bintray.com and verify the addition of files of the new version was uploaded
