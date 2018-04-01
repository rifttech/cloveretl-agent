package ru.krista.etl.cloveretl.agent.identifier;

import org.junit.Test;

import static org.junit.Assert.*;

public class IdentifierStorageTest {
    @Test
    public void testIdentifierStorage() {
        IdentifierStorage identifierStorage = new IdentifierStorage();

        for (int i = 0; i < 10; i++){
            long l = identifierStorage.nextLong();
            System.out.println("RUN_ID " + l);
        }

        identifierStorage.reset();
    }
}