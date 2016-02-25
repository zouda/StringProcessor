package com;

import java.util.ArrayList;

import com.bool.Match;

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
    
    public Sample getSampleAt(int pos){
        return this.SampleList.get(pos);
    }

    public void clearUnMatchedWith(Match m) {
        int i = 0;
        while (true){
            Sample s = SampleList.get(i);
            if (s.isInputMatchedWith(m)){
                i++;
            } else{
                SampleList.remove(i);
            }
            if (i == SampleList.size())
                break;
        }
    }

    public void removeAllSamplesIn(SampleSet ss) {
        for (int i = 0; i < ss.getSize(); i++){
            Sample s = ss.SampleList.get(i);
            this.SampleList.remove(s);
        }
        
    }
}
