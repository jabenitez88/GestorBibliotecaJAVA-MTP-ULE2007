/** Clase controlarLongitud.java 
 * Esta clase sirve para limitar en los JTextField el tamaño de las cadenas
 * que introducimos, por ejemplo, en el campo DNI limito el JTextField a 9,
 * así si intento escribir más no me lo permite.
 */
import javax.swing.text.*;

class controlarLontigud extends PlainDocument
{
	// En numChars almacenarmos el número al que queremos limitar
	// y la variable booleana sirve para saber si en el JTextField
	// se aceptan sólo números o letras y números.
	int numChars = 0;
	boolean nomesNumeros = false;
	public controlarLontigud(int numChars, boolean nomesNumeros)
	{
		this.numChars = numChars;
		this.nomesNumeros = nomesNumeros;
	}

	public void insertString(int offset, String str, AttributeSet a)
	throws BadLocationException
	{	
		boolean valid = true;
		char[] insertChars = str.toCharArray();

		if(insertChars.length + getLength() <= this.numChars)
		{
			if (nomesNumeros)
			{
				for(int i=0;i<insertChars.length;i++)
				{
					if(!Character.isDigit(insertChars[i]))
					{
						valid = false;
						break;
					}
				}
			}
		}
		else
		{
			valid = false;
		}

		if (valid)
		{
			super.insertString(offset, str, a);
		}
		else
		{
			//getToolkit().beep();
		}
	}

}