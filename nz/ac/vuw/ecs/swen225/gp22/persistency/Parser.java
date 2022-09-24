package nz.ac.vuw.ecs.swen225.gp22.persistency;

import java.io.*;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import java.util.stream.*;

import nz.ac.vuw.ecs.swen225.gp22.domain.Domain;

record Point(int row, int col) {
}

public class Parser {

    public static Domain loadLevel(int levelNum) {
        try {
            SAXReader reader = new SAXReader();
            File levelFile = new File("level" + levelNum + ".xml");
            Document document = reader.read(levelFile);

            Element levelElement = document.getRootElement();
            DomainBuilder builder = new DomainBuilder();
            for (Node row : document.selectNodes("/level/row")) {
                Number rowNum = row.numberValueOf("@r");
                if (rowNum == null) {
                    throw new NullPointerException("No row number specified");
                }
                int rowNumInt = rowNum.intValue();
                parseStandardNode(rowNumInt, row.selectNodes("/level/row/wall"), (r, c) -> builder.wall(r, c));
                parseStandardNode(rowNumInt, row.selectNodes("/level/row/exitLock"),
                        (r, c) -> builder.exitLock(r, c));
                parseStandardNode(rowNumInt, row.selectNodes("/level/row/player"),
                        (r, c) -> builder.player(r, c));
                parseStandardNode(rowNumInt, row.selectNodes("/level/row/treasure"),
                        (r, c) -> builder.treasure(r, c));
                parseStandardNode(rowNumInt, row.selectNodes("/level/row/exit"),
                        (r, c) -> builder.exit(r, c));
                parseStandardNode(rowNumInt, row.selectNodes("/level/row/info"),
                        (r, c) -> builder.info(r, c));
                parseColourNode(rowNumInt, row.selectNodes("level/row/key"),
                        (r, c, colour) -> builder.key(r, c, colour));
                parseColourNode(rowNumInt, row.selectNodes("level/row/door"),
                        (r, c, colour) -> builder.door(r, c, colour));
                parsePathNode(rowNumInt, row.selectNodes("level/row/enemy"), (r, c, path) -> builder.enemy(r, c, path));
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void parseStandardNode(int rowNum, List<Node> nodes, BiConsumer<Integer, Integer> consumer) {
        for (Node n : nodes) {
            Number colNum = n.numberValueOf("@c");
            if (colNum == null) {
                throw new NullPointerException("No col number specified");
            }
            consumer.accept(rowNum, colNum.intValue());
        }
    }

    public static void parseColourNode(int rowNum, List<Node> nodes, TriConsumer<Integer, Integer, String> consumer) {
        for (Node n : nodes) {
            Number colNum = n.numberValueOf("@c");
            if (colNum == null) {
                throw new NullPointerException("No col number specified");
            }
            String colour = n.valueOf("@colour");
            if (colour.isEmpty()) {
                throw new IllegalArgumentException("No colour specified");
            }
            consumer.accept(rowNum, colNum.intValue(), colour);
        }
    }

    public static void parsePathNode(int rowNum, List<Node> nodes,
            TriConsumer<Integer, Integer, List<Point>> consumer) {
        for (Node n : nodes) {
            Number colNum = n.numberValueOf("@c");
            if (colNum == null) {
                throw new NullPointerException("No col number specified");
            }
            List<Point> path = new ArrayList<Point>();
            for (Node pathStep : n.selectNodes("level/row/enemy/path")) {
                Number pathRow = pathStep.numberValueOf("@r");
                Number pathCol = pathStep.numberValueOf("@c");
                if (pathRow == null || pathCol == null) {
                    throw new NullPointerException("Path point has not been specified");
                }
                path.add(new Point(pathRow.intValue(), pathCol.intValue()));
            }
            consumer.accept(rowNum, colNum.intValue(), path);
        }
    }
}
