package com.expertsystem;

import java.io.*;
import java.util.HashSet;

public class ExpertSystem implements Serializable{

    private HashSet<Rule> rules;
    private HashSet<Statement> knowledgeBase;

    public ExpertSystem(HashSet<Rule> rules, HashSet<Statement> statements) {
        this.rules = new HashSet<>(rules);
        this.knowledgeBase = new HashSet<>(statements);
        Statement.removeRedundancies(knowledgeBase);
    }

    public ExpertSystem() {
        this.rules = new HashSet<Rule>();
        this.knowledgeBase = new HashSet<Statement>();
    }

    public ExpertSystem(String filepath) {
        /**
         * Loads ExpertSystem from file located at 'filepath'
         */
        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            ExpertSystem ES = (ExpertSystem) objectIn.readObject();
            objectIn.close();

            this.knowledgeBase = ES.knowledgeBase;
            this.rules = ES.rules;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ExpertSystem(File fileES) {
        /**
         * Loads ExpertSystem from file 'fileES'
         */
        this(fileES.getAbsolutePath());
    }

    public HashSet<Rule> getRules() {
        return rules;
    }

    public HashSet<Statement> getKnowledgeBase() {
        return knowledgeBase;
    }

    public boolean addKnowledge(Statement s) {
        return Statement.addTo(knowledgeBase, s);
    }

    public void removeRedundancies() {
        for(Rule R : rules){
            R.removeRedundancies();
        }
        knowledgeBase = Statement.removeRedundancies(knowledgeBase);
    }

    public boolean removeStatement(int index) {
        if(index < 0 || index >= knowledgeBase.size()) {
            return false;
        }

        knowledgeBase.remove(index);
        return true;
    }

    public void clearKnowledgeBase() {
        knowledgeBase = new HashSet<Statement>();
    }

    public boolean addRule(Rule r) {
        rules.add(r);
        return true;
    }

    public boolean removeRule(Rule R) {
        rules.remove(R);
        return true;
    }

    public void clearRules() {
        rules = new HashSet<Rule>();
    }

    public boolean infer(Statement goal){
        HashSet<Rule> remainingRules = new HashSet<>(rules);
        HashSet<Rule> toRemove;
        boolean goal_reached = false;
        boolean canApply = true;
        while(canApply){
            toRemove = new HashSet<>();
            canApply = false;
            for(Rule r : remainingRules){
                if(r.appliedFrom(knowledgeBase)){
                    canApply = true;
                    for(Statement s : r.getConsequences()){
                        addKnowledge(s);
                        if(goal.inferredFrom(s)){
                            goal_reached = true;
                        }
                    }
                    if(goal_reached){
                        return true;
                    }
                    toRemove.add(r);
                }
            }
            remainingRules.removeAll(toRemove);
        }
        return false;
    }

    private HashSet<Integer> getRulesIndexes() {
        HashSet<Integer> ret = new HashSet<Integer>();

        for(int i = 0; i < rules.size(); i++) {
            ret.add(i);
        }

        return ret;
    }

    public void save(String filepath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            objectOut.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void save(File file){
        save(file.getAbsolutePath());
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer("Rules: \n======\n");
        for(Rule R : rules){
            result.append(R.toString() + "\n");
        }
        result.append("\nKnowledgeBase:\n==============\n");
        for(Statement S : knowledgeBase){
            result.append(S.toString() + "\n");
        }
        return result.toString();
    }
}
