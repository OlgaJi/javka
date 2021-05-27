package zoo;

import java.util.Locale;

public class CommandLineArgParser {
    private String[] argv;

    public CommandLineArgParser(String[] argv) {
        this.argv = argv;
    }

    public Zoo parse() {
        Zoo zoo = new Zoo();
        String filePath = "";
        FileType fileType = FileType.JSON;
        for (String arg : argv) {
            if (arg.contains("-configtype")) {
                String[] splitArg = arg.split("=");
                if (splitArg.length != 2) {
                    throw new IllegalArgumentException("Wrong argument");
                }
                switch (splitArg[1]) {
                    case "XML":
                        fileType = FileType.XML;
                        break;
                    case "JSON":
                        fileType = FileType.JSON;
                        break;
                    case "DB":
                        fileType = FileType.DataBase;
                        break;
                }
            } else if (arg.contains("-configfile")) {
                String[] splitArg = arg.split("=");
                if (splitArg.length != 2) {
                    throw new IllegalArgumentException("Wrong argument");
                }
                filePath = splitArg[1] + " ";
            } else {
                filePath = filePath + arg+ " ";
            }
        }
        filePath = filePath.trim();
        switch (fileType) {
            case XML:
                zoo.addAnimalsXml(filePath);
                break;
            case JSON:
                zoo.addAnimals(filePath);
                break;
            case DataBase:
                zoo.addAnimalsDB(filePath);
                break;
        }
        return zoo;
    }

}
