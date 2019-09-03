package guru.springframework.sfgpetclinic.fauxspring;

import guru.springframework.sfgpetclinic.fauxspring.Model;

import java.util.HashMap;

public class ModelMapImpl implements Model {

    HashMap<String,Object> attributes=new HashMap<>();
    @Override
    public void addAttribute(String key, Object o) {
        attributes.put(key,o);
    }

    @Override
    public void addAttribute(Object o) {
//nothing
    }



    public HashMap<String, Object> getAttributes() {
        return attributes;
    }
}
