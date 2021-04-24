import java.util.Scanner;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;   

public class Main {
	
	// Variabel global inisialisai Scanner sebagai (STDIN)
	public static final Scanner scanner = new Scanner(System.in);
	
	// Fungsi main
    public static void main(String[] args) {
		
        System.out.println("===============================================================================");
        System.out.println("|   Selamat Datang (Cinema XXI), Silahkan masukkan konfigurasi denah studio   |");
        System.out.println("===============================================================================");
        
        try {
			// Input Label & Jumlah Kursi Studio
            System.out.print("Masukkan Label Kursi\t\t\t: ");
            String seatLabel = scanner.next();
            System.out.print("Masukkan Jumlah Kursi (1-20)\t: ");
            int seatCount = scanner.nextInt();
            
			if(seatCount <= 20) {
				// Deklarasi & Inisialisasi array seatCode dengan kode label unik berdasarkan Label & Jumlah Kursi
				String[][] seatCode = new String[seatCount][2];
				for(int i=0 ;i<seatCount; i++) {
					seatCode[i][0] = seatLabel +""+ (i+1);	// index 0 adalah tempat meletakkan label unik (misal: A1, A2, A3, ...)
					seatCode[i][1] = null;	// index 1 adalah tempat meletakkan tanggal pesan
				}
				
				// Akses fungsi menuKonfigurasi dengan membawa masukan  Label & Jumlah Kursi dan array seatCode
				menuKonfigurasi(seatLabel, seatCount, seatCode);
			}
			else {
				// Ketika masukan Jumlah Kursi tidak lebih besar dari nilai yang telah ditentukan ( > 20 )
				System.out.println("\nMasukan Jumlah Kursi tidak boleh melebihi 20.");
			}
        }
        catch(Exception e) {
			// Ketika masukan Jumlah Kursi tidak valid / bukan integer / angka
            System.out.println("\nMasukan yang diberikan Tidak Valid!");
        }
    }
	
	// Fungsi yang diakses untuk melakukan konfigurasi lanjutan berupa pesan, batal, dan laporan
	public static void menuKonfigurasi(String seatLabel, int seatCount, String[][] seatCode) {
		System.out.println("\n\n===============================================================================");
		System.out.println("|               Aplikasi Cinema XXI Layout (kursi tersedia "+ seatLabel +"-"+ seatCount +")               |");
		System.out.println("===============================================================================");
           
		System.out.println("A) Pemesanan Kursi");
		System.out.println("B) Batalkan Kursi");
		System.out.println("C) Laporan Denah");
		System.out.println("D) Laporan Transaksi");
		System.out.println("\nMasukkan \'Exit\' untuk keluar.");
		
		System.out.print("\nMasukkan (A/B/C/D/Exit)\t\t\t: ");
		String action = scanner.next();
		
		// Cek apakah masukan (aksi) sama dengan huruf 'A' atau 'a'
		if(action.equalsIgnoreCase("A")) {
			System.out.println("\t\nPilihan Dipilih : A) Pemesanan Kursi");
            System.out.print("\tMasukkan Kode Kursi Yang Hendak Dipesan ");
			for(int i=0 ;i<seatCount; i++) {
				if(i == 0) {
					System.out.print("("+ seatCode[i][0] +"/");
				}
				else if(i == (seatCount-1)) {
					System.out.print(seatCode[i][0] +") : ");
				}
				else {
					System.out.print(seatCode[i][0] +"/");
				}
			}
            String selectedSeat = scanner.next();
			
			// Cek apakah Kode Kursi FREE/SOLD
			int flag = 0;
			
			for(int i=0; i<seatCode.length; i++){
				if(seatCode[i][0].equalsIgnoreCase(selectedSeat)) {
					
					// Jika seatCode[i][1] == null maka kode kursi pada index i masih kosong dan dapat dipesan
				    if(seatCode[i][1] == null) {
						DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
						LocalDateTime now = LocalDateTime.now();  
				        seatCode[i][1] = date.format(now);
				        System.out.println("\n\tBerhasil memesan Kode Kursi "+ selectedSeat + ".");
				    }
				    else {
				        System.out.println("\n\tGagal memesan Kode Kursi. Kode Kursi "+ selectedSeat +" telah dipesan sebelumnya.");
				    }
					flag = 1;
					break;
				}
			}
			// Ketika Masukan tidak tidak terdaftar pada array seatCode
			if(flag == 0) {
				System.out.println("\n\tMasukan yang diberikan Tidak Valid!");
			}
			
			menuKonfigurasi(seatLabel, seatCount, seatCode);	// Fungsi Rekursif
		}
		
		// Cek apakah masukan (aksi) sama dengan huruf 'B' atau 'b'
		else if(action.equalsIgnoreCase("B")) {
			System.out.println("\t\nPilihan Dipilih : B) Batalkan Kursi");
            System.out.print("\tMasukkan Kode Kursi Untuk Dibatalkan ");
			for(int i=0 ;i<seatCount; i++) {
				if(i == 0) {
					System.out.print("("+ seatCode[i][0] +"/");
				}
				else if(i == (seatCount-1)) {
					System.out.print(seatCode[i][0] +") : ");
				}
				else {
					System.out.print(seatCode[i][0] +"/");
				}
			}
            String selectedSeat = scanner.next();
			
			// Cek apakah Kode Kursi FREE/SOLD
			int flag = 0;
			
			for(int i=0; i<seatCode.length; i++){
				if(seatCode[i][0].equalsIgnoreCase(selectedSeat)) {
				    if(seatCode[i][1] != null) {
				        seatCode[i][1] = null;
				        System.out.println("\n\tBerhasil membatalkan pesanan pada Kode Kursi "+ selectedSeat + ".");
				    }
				    else {
				        System.out.println("\n\tGagal membatalkan pesanan Kode Kursi. Kode Kursi "+ selectedSeat +" belum pernah dipesan.");
				    }
					flag = 1;
					break;
				}
			}
			// Ketika Masukan tidak tidak terdaftar pada array seatCode
			if(flag == 0) {
				System.out.println("\n\tMasukan yang diberikan Tidak Valid!");
			}
			
			menuKonfigurasi(seatLabel, seatCount, seatCode);	// Fungsi Rekursif
		}
		
		// Cek apakah masukan (aksi) sama dengan huruf 'C' atau 'c'
		else if(action.equalsIgnoreCase("C")) {
			System.out.println("\t\nPilihan Dipilih : C) Laporan Denah");
			System.out.println("\t==================================");
			
            for(int i=0; i<seatCode.length; i++){
				if(seatCode[i][1] == null) {
					System.out.format("\t|\t %s \t|\t KOSONG (FREE) \t |", seatCode[i][0]);
				}
				else {
					System.out.format("\t|\t %s \t|\t PENUH (SOLD) \t |", seatCode[i][0]);
				}
				System.out.println("");
            }
			System.out.println("\t==================================");
			
			menuKonfigurasi(seatLabel, seatCount, seatCode);	// Fungsi Rekursif
		}
		
		// Cek apakah masukan (aksi) sama dengan huruf 'D' atau 'd'
		else if(action.equalsIgnoreCase("D")) {
			System.out.println("\t\nPilihan Dipilih : D) Laporan Transaksi");
			
			int freeSeat = 0;
			int soldSeat = 0;
			System.out.println("\t==========================================");
            for(int i=0; i<seatCode.length; i++){
				if(seatCode[i][1] == null) {
					System.out.format("\t|\t %s \t|\t\t\t\t\t\t\t|", seatCode[i][0]);
					freeSeat += 1;
				}
				else {
					soldSeat += 1;
					System.out.format("\t|\t %s \t|\t %s \t|", seatCode[i][0], seatCode[i][1]);
				}
				System.out.println("");
            }
            System.out.println("\t==========================================");
            System.out.println("\n\tTotal : Terdapat "+ freeSeat +" Kursi Kosong (FREE) dan "+ soldSeat +" Kursi yang telah Dipesan (SOLD).");
			
			menuKonfigurasi(seatLabel, seatCount, seatCode);	// Fungsi Rekursif
		}
		
		// Cek apakah masukan (aksi) sama dengan huruf 'exit' atau 'EXIT' atau sejenisnya tanpa memperhatikan case/besar kecil huruf
		else if(action.equalsIgnoreCase("exit")) {
			// Akhir dari aplikasi
			System.out.println("\nBerhasil keluar dari Aplikasi.");
		}
		
		// Ketika masukan yang diberikan bukan samadengan huruf A-D/exit
		else {
			System.out.println("\nMasukan yang diberikan Tidak Valid!");
			System.out.println("Silakan berikan masukkan dengan pilihan yang tersedia (A/B/C/D) atau ketik \'Exit\' untuk keluar.");
			menuKonfigurasi(seatLabel, seatCount, seatCode);	// Fungsi Rekursif
		}
		
		scanner.close();
	}
}