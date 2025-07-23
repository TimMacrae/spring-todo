package com.ecosystem.todojava.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangeHistoryServiceTest {
    @Test
    void recordChange_and_undoLastChange_shouldRunUndoAction() {
        ChangeHistoryService service = new ChangeHistoryService();
        final boolean[] called = {false};

        service.recordChange(() -> called[0] = true);
        assertTrue(service.undoLastChange());
        assertTrue(called[0]);
    }

    @Test
    void undoLastChange_shouldReturnFalseIfNoChange() {
        ChangeHistoryService service = new ChangeHistoryService();
        assertFalse(service.undoLastChange());
    }

    @Test
    void undoLastChange_shouldUndoMultipleChangesInOrder() {
        ChangeHistoryService service = new ChangeHistoryService();
        StringBuilder sb = new StringBuilder();

        service.recordChange(() -> sb.append("A"));
        service.recordChange(() -> sb.append("B"));

        assertTrue(service.undoLastChange());
        assertEquals("B", sb.toString());

        assertTrue(service.undoLastChange());
        assertEquals("BA", sb.toString());

        assertFalse(service.undoLastChange());
    }

}