package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Optional;

import java.util.stream.Collectors;

import java.util.Comparator;
import java.util.Collections;




import br.com.codenation.desafio.annotation.Desafio;
import br.com.codenation.desafio.app.MeuTimeInterface;
import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;

public class DesafioMeuTimeApplication implements MeuTimeInterface {

	private HashMap<Long, String> teamNames = new HashMap<Long, String>();
	private HashMap<Long, LocalDate> dateOfCreation = new HashMap<Long, LocalDate>();
	private HashMap<Long, String> mainUniformColor = new HashMap<Long, String>();
	private HashMap<Long, String> secondaryUniformColor = new HashMap<Long, String>();

	private HashMap<Long, String> playerNames = new HashMap<Long, String>();
	private HashMap<Long, Long> playerTeam = new HashMap<Long, Long>();
	private HashMap<Long, LocalDate> playerBirth = new HashMap<Long, LocalDate>();
	private HashMap<Long, Integer> habilityLevel = new HashMap<Long, Integer>();
	private HashMap<Long, BigDecimal> playerWage = new HashMap<Long, BigDecimal>();
	private HashMap<Long, Boolean> isCapitan = new HashMap<Long, Boolean>();

	//Map<Integer, Integer> mapa = new HashMap<Integer, Integer>();


	@Desafio("buscarJogadoresDoTime")
	public List<Long> buscarJogadoresDoTime(Long idTime) {
		List<Long> myList = new ArrayList<Long>();

		if (!teamNames.containsKey(idTime)){
			throw new TimeNaoEncontradoException();
		}
		else{
			for (Map.Entry<Long, Long> entry : playerTeam.entrySet()) {
				if (entry.getValue().equals(idTime) ) {
					myList.add(entry.getKey());
				}

			} 
			Collections.sort(myList);
			return myList;
		}


	}


	@Desafio("incluirTime")
	public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
		
		//if (teamNames.containsKey(id)){
		if (teamNames.containsKey(id)){
			throw new IdentificadorUtilizadoException();
		}
		else{
			teamNames.put(id, nome);
			dateOfCreation.put(id, dataCriacao);
			mainUniformColor.put(id, corUniformePrincipal);
			secondaryUniformColor.put(id, corUniformeSecundario);
		}

	} 



	@Desafio("incluirJogador")
	public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
		
		if (!teamNames.containsKey(idTime)){
			throw new TimeNaoEncontradoException();
		}

		if (playerNames.containsKey(id)){
			throw new IdentificadorUtilizadoException();
		}
		else{
			playerNames.put(id, nome);
			playerTeam.put(id, idTime);
			playerBirth.put(id, dataNascimento);
			habilityLevel.put(id, nivelHabilidade);
			playerWage.put(id, salario);
			isCapitan.put(id, false);
		}

	}

/*
	@Desafio("definirCapitao")
	public void definirCapitao(Long idJogador) {
		Long team;
		List<Long> myList = new ArrayList<Long>();

		if (!playerNames.containsKey(idJogador)){
			throw new br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException();
		}
		else{
			myList = buscarJogadoresDoTime(idTime);
			team = playerTeam.get(idJogador);
			for (Map.Entry<Long, Long> entry : playerTeam.entrySet()) {
				if (entry.getValue().equals(team) ) {
					isCapitan.put(entry.getKey(), false);
				}

			}
			isCapitan.put(idJogador, true);
		}

	} */


	@Desafio("definirCapitao")
	public void definirCapitao(Long idJogador) {
		//Long team;
		List<Long> myList = new ArrayList<Long>();

		if (!playerNames.containsKey(idJogador)){
			throw new JogadorNaoEncontradoException();
		}
		else{
			myList = buscarJogadoresDoTime( playerTeam.get(idJogador) );
			for (Long player : myList){
				if ( player.equals(idJogador) ) {
					isCapitan.put(idJogador, true);
				}
				else{
					isCapitan.put(idJogador, false);
				}
			}

		}

	} 


	@Desafio("buscarCapitaoDoTime")
	public Long buscarCapitaoDoTime(Long idTime) {
		Boolean hasCaptain = false;
		Long capitan = 0l;
		//List<Long> myList = new ArrayList<Long>();


		if (!teamNames.containsKey(idTime)){
			throw new TimeNaoEncontradoException();
		}
		else{
			//team = playerTeam.get(idJogador);
			for (Map.Entry<Long, Long> entry : playerTeam.entrySet()) {
				//if (entry.getValue() == idTime ) {
				if (entry.getValue().equals(idTime)) {
					if (isCapitan.get(entry.getKey())){
						hasCaptain = true;
						capitan = entry.getKey();
					}
				}

			}		
		}
		if (hasCaptain == false){
			throw new CapitaoNaoInformadoException();
		} 

		return capitan;

	}



	@Desafio("buscarNomeJogador")
	public String buscarNomeJogador(Long idJogador) {

		if (!playerNames.containsKey(idJogador)){
			throw new JogadorNaoEncontradoException();
		}
		else{
			return playerNames.get(idJogador);
		}

	}

	@Desafio("buscarNomeTime")
	public String buscarNomeTime(Long idTime) {

		if (!teamNames.containsKey(idTime)){
			throw new TimeNaoEncontradoException();
		}
		else{
			return teamNames.get(idTime);
		}		

	}



	@Desafio("buscarMelhorJogadorDoTime")
	public Long buscarMelhorJogadorDoTime(Long idTime) {
		List<Long> myList = new ArrayList<Long>();
		Long bestPlayer = 0l;

		if (!teamNames.containsKey(idTime)){
			throw new TimeNaoEncontradoException();
		}
		else{
			myList = buscarJogadoresDoTime(idTime);

			for (Long player : myList){
				if (bestPlayer.equals(0l)){
					bestPlayer = player;
				}
				if (habilityLevel.get(player) > habilityLevel.get(bestPlayer)){
					bestPlayer = player;
				}
			}
		}
		return bestPlayer;
	}



	@Desafio("buscarJogadorMaisVelho")
	public Long buscarJogadorMaisVelho(Long idTime) {
		Long oldestPlayer = 0l;
		List<Long> myList = new ArrayList<Long>();

		if (!teamNames.containsKey(idTime)){
			throw new TimeNaoEncontradoException();
		}
		else{
			myList = buscarJogadoresDoTime(idTime);
			oldestPlayer = Collections.max(myList);
			for (Long player : myList){
				if (oldestPlayer.equals(0l)){
					oldestPlayer = player;
				}
				if (playerBirth.get(player).isBefore( playerBirth.get(oldestPlayer))) {
					oldestPlayer = player;
				}
				if 	(playerBirth.get(player).isEqual(playerBirth.get(oldestPlayer))) {
					if (player < oldestPlayer){
						oldestPlayer = player;
					}
				}
			}

		}

		return oldestPlayer;
	}

	@Desafio("buscarTimes")
	public List<Long> buscarTimes() {
		List<Long> myList = new ArrayList<Long>();

		for (Map.Entry<Long, String> entry : teamNames.entrySet()) {
			myList.add(entry.getKey());
		}

		return myList;

	}


	@Desafio("buscarJogadorMaiorSalario")
	public Long buscarJogadorMaiorSalario(Long idTime) {
		List<Long> myList = new ArrayList<Long>();
		Long biggestWage = 0l;

		if (!teamNames.containsKey(idTime)){
			throw new TimeNaoEncontradoException();
		}		
		else{
			myList = buscarJogadoresDoTime(idTime);
			biggestWage = Collections.max(myList);
			for (Long player : myList){
				if ( playerWage.get(player).compareTo(playerWage.get(biggestWage)) == 1 ){
					biggestWage = player;
				}
				if ( playerWage.get(player).compareTo(playerWage.get(biggestWage)) == 0 ){
					if (player < biggestWage){
						biggestWage = player;
					}
				}
			}

		} 
		return biggestWage;

	}

	@Desafio("buscarSalarioDoJogador")
	public BigDecimal buscarSalarioDoJogador(Long idJogador) {
		BigDecimal wage;

		if (!playerNames.containsKey(idJogador)){
			throw new JogadorNaoEncontradoException();
		}	
		else{
			wage = playerWage.get(idJogador);
		}
		return wage;

	}


/*
	@Desafio("buscarTopJogadores")
	public List<Long> buscarTopJogadores(Integer top) {
		List<Long> myList = new ArrayList<Long>();
		List<Long> myList2 = new ArrayList<Long>();

		for (Integer i = 0; i < top; i++){
			myList.add(0l);
		}

		for (Map.Entry<Long, Long> entry : playerTeam.entrySet()) {
			
			for (Integer i = 0; i < top; i++){
				if ( habilityLevel.get( entry.getKey() ) > habilityLevel.get( myList.get(i) ) ){
					myList.add(i, entry.getKey());
					break;
				}
				else{
					if ( habilityLevel.get( entry.getKey() ) == habilityLevel.get( myList.get(i) ) ){
						if ( entry.getKey() < myList.get(i) ){
							myList.add(i, entry.getKey());
							break;
						}
					}
				}


			}
		}

		for (Integer j = 0; j < top; j++){
			myList2.add(myList.get(j));
		}		

		return myList2;
	}



	*/

	@Desafio("buscarTopJogadores")
	public List<Long> buscarTopJogadores(Integer top) {
		List<Long> myList = new ArrayList<Long>();
		List<Long> myList2 = new ArrayList<Long>();

		for (Map.Entry<Long, Long> entry : playerTeam.entrySet()) {		
			for (Integer i = 0; i < top; i++){
				if ( habilityLevel.get( entry.getKey() ) > habilityLevel.get( myList.get(i) ) ){
					myList.add(i, entry.getKey());
					break;
				}
				else{
					if ( habilityLevel.get( entry.getKey() ) == habilityLevel.get( myList.get(i) ) ){
						if ( entry.getKey() < myList.get(i) ){
							myList.add(i, entry.getKey());
							break;
						}
					}
				}
			}
		}

		for (Integer j = 0; j < top; j++){
			myList2.add(myList.get(j));
		}		

		return myList2;
	}






	@Desafio("buscarCorCamisaTimeDeFora")
	public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {

		if ( (teamNames.get(timeDaCasa) == null)||(teamNames.get(timeDeFora) == null) ){
			throw new TimeNaoEncontradoException();
		}	

		if ( (mainUniformColor.get(timeDaCasa).toUpperCase()).equals( (mainUniformColor.get(timeDeFora)).toUpperCase() ) ) {
			return secondaryUniformColor.get(timeDeFora);
		}
		else{
			return mainUniformColor.get(timeDeFora);
		}

	}

}

