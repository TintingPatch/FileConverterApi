# FileConverter
### by TintingPatch

> [!Caution]
> If an issue occours please let me know.

## Filetypes
Filetypes are as simple as it can get.
This are classes witch can read in a file and converts it to a bufferedImage.
`read(File file) throws IOException, CouldNotConvertFileException`

Also it can give the filetype out to a target location.
`write(File file, BufferedImage image)`

> [!Tip]
> The GIFImage class also has a `List<BufferedImage> readEveryImage(File file)` class to read every image from the gif.


## Utils
### FileTypeUtils
The FileTypeUtils class currently only has one function:
The `String[] listFiletypesByType(String ending)` function.
This is to get a list of all filetypes a file can be converted to.

## Enums
### ImageFileTypes
ImageFileTypes is an enum containing every filetype currently supported by the programm.

## Interfaces
### ImageFileType
This interface contains 3 functions.
`BufferedImage read(File file)` to read the file.
`void write(File file, BufferedImage image)` to write the file.
`ImageFiletypes getImageFileType()` to get the filetype.
