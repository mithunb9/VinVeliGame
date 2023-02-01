/**
 * Loads the tile map of the planets so that is visible in the screen during the gameplay.
 * It includes methods to obtain the image for the tilemap from the file and also
 * getting the ship icons.
 * @author: Mithun Balasubramaniam
 * Collaborators: Sree Bommisetty, Nikhil Venkatachalam
 * Teacher name: Ms. Bailey
 * Period: 5
 * Due Date: 5/19/2022
 */
package main.java.game.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class TileMapLoader {
    public static final int PLANET_TILE_SIZE = 80;

    /**
     * Loads the tile map of the planets.
     * @return an ArrayList of the buffered image type with the tile map of the planets.
     * @throws IOException when the image is not read correctly.
     * @throws URISyntaxException
     */
    public static ArrayList<BufferedImage> loadPlanetTileMap() throws IOException, URISyntaxException {
        BufferedImage planetTileMap =
                ImageIO.read(TileMapLoader.class.getResourceAsStream
                        ("/main/resources/assets/planets.png"));
        ArrayList<BufferedImage> planetTileArray = new ArrayList<>();

        int rows = (2 + 2 + 2);

        for (int i = 0; i < rows; i++) {
            planetTileArray.add(planetTileMap.getSubimage(0, PLANET_TILE_SIZE*i, PLANET_TILE_SIZE,
                    PLANET_TILE_SIZE));
            planetTileArray.add(planetTileMap.getSubimage(PLANET_TILE_SIZE, PLANET_TILE_SIZE*i,
                    PLANET_TILE_SIZE, PLANET_TILE_SIZE));
            planetTileArray.add(planetTileMap.getSubimage(PLANET_TILE_SIZE * 2,
                    PLANET_TILE_SIZE*i, PLANET_TILE_SIZE, PLANET_TILE_SIZE));
        }

        return planetTileArray;
    }

    /**
     * Retrieves the file as an IO stream
     * @param fileName the file to retrieve
     * @return the IO stream based on the file
     */
    public InputStream getFileAsIOStream(final String fileName)
    {
        InputStream ioStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(fileName);

        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
    }

    /**
     * Gets the ship icons as an array of buffered images.
     * @return an array of buffered images of the ship icons.
     * @throws IOException when the icon is not read correctly from the IO.
     */
    public static BufferedImage[] getShipIcons() throws IOException {
        try {
            BufferedImage[] shipIconArray = new BufferedImage[(2 * 2)];

            for (int i = 0; i < shipIconArray.length / 2; i++) {
                shipIconArray[i] = ImageIO.read(TileMapLoader.class.getResourceAsStream
                        ("/main/resources/assets/warship1.png"));
                shipIconArray[i+1] = ImageIO.read(TileMapLoader.class.getResourceAsStream
                        ("/main/resources/assets/transport1.png"));
            }

            return shipIconArray;
        } catch (Exception e) {
            throw new IOException();
        }
    }
}
