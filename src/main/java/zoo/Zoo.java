package zoo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for concrete zoo
 */
@Getter
public class Zoo {
    /**
     * List of all animals in the zoo
     */
    private List<AnimalSpecies> zooAnimalSpecies;

    /**
     * Static field for storing state of ALL carnivore animals
     * can be transformed to List for containing states of a concrete type (e.g. Lion)
     */
    @Getter @Setter
    private static AnimalState allCarnivoreState;

    /**
     * Static field for storing state of ALL herbivore animals
     * can be transformed to List for containing states of a concrete type (e. g. Cow)
     */
    @Getter @Setter
    private static AnimalState allHerbivoreState;

    /**
     * Initialization of animals list and default states of animal types
     */
    public Zoo() {

        zooAnimalSpecies = new LinkedList<>();
        allCarnivoreState = AnimalState.CALM;
        allHerbivoreState = AnimalState.CALM;
    }

    /**
     * Method for adding animals to the zoo from the specified JSON file
     *
     * @param jsonPath path to JSON file with animals info
     */
    public void addAnimals(String jsonPath) {
        ObjectMapper mapper = new ObjectMapper();
        File animalsFile = new File(jsonPath);
        try {
            AnimalsDataFile animalsData = mapper.readValue(animalsFile, AnimalsDataFile.class);
            zooAnimalSpecies.addAll(animalsData.getCarnivoreAnimals());
            zooAnimalSpecies.addAll(animalsData.getHerbivoreAnimals());
        } catch (IOException e) {
            System.out.println(e.toString());
            throw new IllegalStateException("File hasn't been parsed");
        }
    }

    /**
     * Method for adding animals to the zoo from the specified XML file
     *
     * @param xmlPath path to XML file with animals info
     */
    public void addAnimalsXml(String xmlPath) {
        ObjectMapper mapper = new XmlMapper();
        File animalsFile = new File(xmlPath);
        try {
            AnimalsDataFile animalsData = mapper.readValue(animalsFile, AnimalsDataFile.class);
            zooAnimalSpecies.addAll(animalsData.getCarnivoreAnimals());
            zooAnimalSpecies.addAll(animalsData.getHerbivoreAnimals());
        } catch (IOException e) {
            System.out.println(e.toString());
            throw new IllegalStateException("File hasn't been parsed");
        }
    }

    public void addAnimalsDB(String url) {
        try {
            loadFromDataBase(url);
        } catch (SQLException e) {
            System.out.println("Database " + url + " error to load animals");
            System.out.println(e);
        }
    }

    public void loadFromDataBase(String url) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e){
            System.out.println("Cant found arg.postgresql.Driver class");
        }
        try (Connection connection = DriverManager.getConnection(url, "zoo_user", "zoo_psw")) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from animals")) {
                try (ResultSet animalsSet = preparedStatement.executeQuery()) {
                    List<AnimalSpecies> animals = new ArrayList<>();
                    while (animalsSet.next()) {
                        String name = animalsSet.getString("name");
                        int amount = animalsSet.getInt("amount");
                        AnimalType animalType = AnimalType.valueOf(animalsSet.getString("type").toUpperCase());
                        switch (animalType) {
                            case CARNIVORE:
                                Carnivore carnivore = new Carnivore();
                                carnivore.setName(name);
                                carnivore.setAmount(amount);
                                animals.add(carnivore);
                                break;
                            case HERBIVORE:
                                Herbivore herbivore = new Herbivore();
                                herbivore.setName(name);
                                herbivore.setAmount(amount);
                                animals.add(herbivore);
                                break;
                            default:
                                throw new IllegalStateException("Animal type " + animalType + " is not supported");
                        }
                    }
                    zooAnimalSpecies.addAll(animals);
                }
            }
        }
    }

    /**
     * Method for handling user actions with the specified type of animals
     *
     * @param event event to do a certain actions with animals
     * @param animalType type of animals for event
     */
    public void performAction(Events event, AnimalType animalType) {
        updateAllSpeciesCurrentStates();
        switch (animalType) {
            case CARNIVORE:
                for(AnimalSpecies species : zooAnimalSpecies) {
                    if(species instanceof Carnivore) {
                        species.updateState(event);
                    }
                }
                break;
            case HERBIVORE:
                for(AnimalSpecies species : zooAnimalSpecies) {
                    if(species instanceof Herbivore) {
                        species.updateState(event);
                    }
                }
                break;
            default:
                System.out.println("No such animal type in the zoo");
        }
        updateAllSpeciesCurrentStates();
    }

    /**
     * Method for handling user actions with ALL animals
     *
     * @param event event to do a certain actions with ALL animals
     */
    public void performAction(Events event) {
        updateAllSpeciesCurrentStates();
        for(AnimalSpecies species : zooAnimalSpecies) {
            species.updateState(event);
        }
        updateAllSpeciesCurrentStates();
    }

    /**
     * Method for updating current state of ALL animals
     * Used as a way for animals, to be aware of a current state of their type
     */
    private void updateAllSpeciesCurrentStates() {
        for (AnimalSpecies animal : zooAnimalSpecies) {
            animal.updateState(Events.UPDATE_STATE);
        }
    }

    /**
     * NOTE: ONLY A DEBUGGING AND REPRESENTATION METHOD
     * MAY NOT BE USED IN ACTUAL MODEL
     *
     * Used for printing states of concrete animals
     * and states of carnivore/herbivore types in general
     */
    public void printAllStates() {
        System.out.println("\n --- CURRENT ZOO INFO --- ");
        System.out.println("Carnivore state: " + allCarnivoreState);
        System.out.println("Herbivore state: " + allHerbivoreState);
        for (AnimalSpecies animal : zooAnimalSpecies) {
            animal.printDescription();
        }
    }
}
