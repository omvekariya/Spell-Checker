import java.io.*;

public class Dictionary {
    public String fileName;
    public Trie t;

    public Dictionary(String fileName) {
        this.fileName = fileName;
        loadFile();
    }

    public void loadFile() {
        t = new Trie();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = "";
            while ((line = br.readLine()) != null) {
                t.insert(line.toLowerCase());
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String findClosest(String toFind) {
        return t.finder(toFind);
    }

    public boolean addWord(String newWord){
        if (t.search(newWord)) {
            return false;
        }
        t.insert(newWord);
        return true;
    }

    public boolean delWord(String delWord){
        if (t.search(delWord)) {
            t.delete(delWord);
            return true;
        }
        return false;
    }

    public void updateDict(){
        try {
            t.updateDict();
        } catch (IOException e) {
            //pass
        }
    }
}