package com.jamiecheung.apps.columneddataformatter.model;

import static com.jamiecheung.apps.columneddataformatter.constants.CommonConstants.NEW_LINE;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class representing the properties of the input file or input String
 * containing the columned data contents.
 *
 * @author JamieCheung
 *
 */
public class ColumnedData {

    private ZonedDateTime creationTimeStamp;
    private String delimiter;
    private String contents;
    private String inputFile;
    private String resultDirectory;
    private List<String> linesAsStrings;

    /**
     * Creates a ColumnedData with the specified characteristics.
     * 
     * @param creationTimeStamp
     * @param delimiter
     * @param contents
     */
    public ColumnedData(ZonedDateTime creationTimeStamp, String delimiter, String contents) {
        super();
        this.creationTimeStamp = creationTimeStamp;
        this.delimiter = delimiter;
        this.contents = contents;
        this.linesAsStrings = Arrays.asList(contents.split(NEW_LINE));
    }

    /**
     * Gets the {@link ColumnedData#creationTimeStamp}
     * 
     * @return the {@link ColumnedData#creationTimeStamp}
     */
    public ZonedDateTime getCreationTimeStamp() {
        return creationTimeStamp;
    }

    /**
     * Gets the {@link ColumnedData#delimiter}
     *
     * @return the {@link ColumnedData#delimiter}
     */
    public String getDelimiter() {
        return delimiter;
    }

    /**
     * Sets the {@link ColumnedData#delimiter}
     *
     * @param delimiter
     *                  the {@link ColumnedData#delimiter} to set
     */
    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * Gets the {@link ColumnedData#contents}
     *
     * @return the {@link ColumnedData#contents}
     */
    public String getContents() {
        return contents;
    }

    /**
     * Sets the {@link ColumnedData#contents}
     *
     * @param contents
     *                 the {@link ColumnedData#contents} to set
     */
    public void setContents(String contents) {
        this.contents = contents;
    }

    /**
     * Gets the {@link ColumnedData#inputFile}
     *
     * @return the {@link ColumnedData#inputFile}
     */
    public String getInputFile() {
        return inputFile;
    }

    /**
     * Sets the {@link ColumnedData#inputFile}
     *
     * @param inputFilePath
     *                      the {@link ColumnedData#inputFile} to set
     */
    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    /**
     * Gets the {@link ColumnedData#resultDirectory}
     *
     * @return the {@link ColumnedData#resultDirectory}
     */
    public String getResultDirectory() {
        return resultDirectory;
    }

    /**
     * Sets the {@link ColumnedData#resultDirectory}
     *
     * @param resultDirectory
     *                        the {@link ColumnedData#resultDirectory} to set
     */
    public void setResultDirectory(String resultDirectory) {
        this.resultDirectory = resultDirectory;
    }

    /**
     * Gets a copy of the {@link ColumnedData#linesAsStrings}
     *
     * @return a copy of the {@link ColumnedData#linesAsStrings}
     */
    public List<String> getLinesAsStrings() {
        return new ArrayList<>(linesAsStrings);
    }

}
