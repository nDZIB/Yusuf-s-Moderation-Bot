/*
 * GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007 Copyright (C) 2021 Free Software Foundation,
 * Inc. <https://fsf.org/> Everyone is permitted to copy and distribute verbatim copies of this
 * license document, but changing it is not allowed. Yusuf Arfan Ismail The GNU General Public
 * License is a free, copyleft license for software and other kinds of works.
 */

package io.github.yusufsdiscordbot.yusufsmoderationbot;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 * Were the database file is registered and created. The file location for the database is stored in
 * this class and the process of creating the database file is coded here.
 *
 * @author Yusuf Arfan Ismail
 */
public class DataBase {
    private static final Logger logger = LoggerFactory.getLogger(DataBase.class);
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource dataSource;

    static {
        try {
            final File dbFile = new File(Config.get("DATABASE_FILE_LOCATION"));

            if (!dbFile.exists()) {
                if (dbFile.createNewFile()) {
                    logger.info("Created database file");
                } else {
                    logger.info("Could not create database file");
                }
            }
        } catch (IOException e) {
            logger.error("Error while loading database", e);
        }

        config.setJdbcUrl(Config.get("DATABASE_URL"));
        config.setUsername("");
        config.setPassword("");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        dataSource = new HikariDataSource(config);

        try (final Statement statement = getConnection().createStatement()) {
            File folder = new File("application/src/main/resources/database");
            File[] listOfFiles = folder.listFiles();

            for (File file : Objects.requireNonNull(listOfFiles)) {
                if (file.isFile()) {
                    statement.execute(new String(Files.readAllBytes(Path.of(file.getPath()))));
                }
            }
            logger.info("Database table created");
            statement.closeOnCompletion();
            logger.info("Database table closed");
        } catch (SQLException | IOException e) {
            logger.error("Error while loading database", e);
        }
    }

    private DataBase() {}

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
