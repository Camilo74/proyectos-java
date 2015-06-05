package ar.com.q3s.qfolder.backend.ws;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class RegisterApplication extends Application
{
   private Set<Object> singletons = new HashSet<Object>();
   private Set<Class<?>> empty = new HashSet<Class<?>>();

   public RegisterApplication()
   {
      singletons.add(new ListFileWS());
   }

   @Override
   public Set<Class<?>> getClasses()
   {
      return empty;
   }

   @Override
   public Set<Object> getSingletons()
   {
      return singletons;
   }
}