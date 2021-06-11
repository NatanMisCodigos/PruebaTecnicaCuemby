package co.com.cuemby.mobile.pruebatecnicacuemby.model;

import java.util.List;

public class HeroApi {

    private String response;
    private List<Results> results;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public class Results{

        private String id;
        private String name;
        private Powerstats powerstats;
        private Image image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Powerstats getPowerstats() {
            return powerstats;
        }

        public void setPowerstats(Powerstats powerstats) {
            this.powerstats = powerstats;
        }

        public Image getImage() {
            return image;
        }

        public void setImage(Image image) {
            this.image = image;
        }
    }

    public class Powerstats{

        private String speed;
        private String power;
        private String strength;

        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }

        public String getPower() {
            return power;
        }

        public void setPower(String power) {
            this.power = power;
        }

        public String getStrength() {
            return strength;
        }

        public void setStrength(String strength) {
            this.strength = strength;
        }
    }

    public class Image{
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
