package cs360.team4.project;
import java.util.ArrayList;

/**
 * @author Cooper
 *
 */
public class Event<E> {
	int Eid;
	int feeder[];
	School host;
	ArrayList<E> list;

	public Event() {
		Eid = -1;
		feeder[16]=-1;
		host = null;
		list = null;
	}
	
	public ArrayList<E> generateList(E element){
		for(int i=0; i<feeder.length-1; i++){
			if(feeder[i]>0){
				this.list.add(i, element);	
			}
		}
		
		return this.list;
	}

	public class Final_Event extends Event {
		public Final_Event() {
			this.list = new ArrayList<Semi_Event>();
		}
	}

	public class Semi_Event extends Event {
		public Semi_Event() {
			this.list = new ArrayList<Reg_Event>();
		}
	}

	public class Reg_Event extends Event {
		public Reg_Event() {
			this.list = new ArrayList<Sec_Event>();
		}
	}

	public class Sec_Event extends Event {
		public Sec_Event() {
			this.list = new ArrayList<School>();
		}
	}

	/**
	 * @return the eid
	 */
	public int getEid() {
		return Eid;
	}

	/**
	 * @param eid the eid to set
	 */
	public void setEid(int eid) {
		Eid = eid;
	}

	/**
	 * @return the feeder
	 */
	public int[] getFeeder() {
		return feeder;
	}

	/**
	 * @param feeder the feeder to set
	 */
	public void setFeeder(int[] feeder) {
		this.feeder = feeder;
	}

	/**
	 * @return the host
	 */
	public School getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(School host) {
		this.host = host;
	}

	/**
	 * @return the list
	 */
	public ArrayList<E> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(ArrayList<E> list) {
		this.list = list;
	}
}
