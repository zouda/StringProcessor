package com;

import java.util.ArrayList;

/*
 * Set of Samples
 */
public class SampleSet {
    private ArrayList<Sample> SampleList;
    
    public SampleSet(){
        this.SampleList = new ArrayList<Sample>();
    }
    
    public void addSample(Sample s){
        this.SampleList.add(s);
    }
    
    public void removeSample(Sample s){
        this.SampleList.remove(s);
    }

    public void addSampleSet(ArrayList<Sample> sl) {
        this.SampleList.addAll(sl);
    }

    public boolean isEmpty() {
        return (this.SampleList.size() == 0);
    }

    public SampleSet Clone() {
        SampleSet ss = new SampleSet();
        ss.addSampleSet(this.SampleList);
        return ss;
    }

    public int getSize() {
        return this.SampleList.size();
    }
}
