package com.ecosystem.todojava.service;

import org.springframework.stereotype.Service;

import java.util.Stack;

@Service
public class ChangeHistoryService {
    private final Stack<Runnable> undoStack = new Stack<>();

    public void recordChange(Runnable undoAction) {
        undoStack.push(undoAction);
    }

    public boolean undoLastChange() {
        if (undoStack.isEmpty()) return false;
        undoStack.pop().run();
        return true;
    }
}
