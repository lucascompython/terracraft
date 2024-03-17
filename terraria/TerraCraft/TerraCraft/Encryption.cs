using Google.Protobuf;
using System.Security.Cryptography;
using System.Text;
namespace TerraCraft
{
    internal sealed class Encryption
    {
        private readonly byte[] keyBytes;
        private readonly Aes aesAlg;


        public Encryption(string token)
        {
            if (token.Length != 32)
            {
                throw new ArgumentException("The token must be 32 characters long");
            }
            keyBytes = Encoding.UTF8.GetBytes(token);
            aesAlg = Aes.Create();
            aesAlg.Key = keyBytes;
            aesAlg.Mode = CipherMode.CBC;
            aesAlg.Padding = PaddingMode.PKCS7;
        }

        public EncryptedData Encrypt(string plainText)
        {
            aesAlg.GenerateIV();
            var encryptor = aesAlg.CreateEncryptor(aesAlg.Key, aesAlg.IV);
            using var msEncrypt = new MemoryStream();

            using var csEncrypt = new CryptoStream(msEncrypt, encryptor, CryptoStreamMode.Write);
            byte[] plainTextBytes = Encoding.UTF8.GetBytes(plainText);
            csEncrypt.Write(plainTextBytes, 0, plainTextBytes.Length);
            csEncrypt.FlushFinalBlock();

            return new EncryptedData
            {
                Data = ByteString.CopyFrom(msEncrypt.ToArray()),
                Iv = ByteString.CopyFrom(aesAlg.IV)
            };
        }

        public string Decrypt(EncryptedData data)
        {
            var decryptor = aesAlg.CreateDecryptor(aesAlg.Key, data.Iv.ToByteArray());
            using var msDecrypt = new MemoryStream(data.Data.ToByteArray());
            using var csDecrypt = new CryptoStream(msDecrypt, decryptor, CryptoStreamMode.Read);
            using var srDecrypt = new StreamReader(csDecrypt);
            return srDecrypt.ReadToEnd();
        }
    }
}
