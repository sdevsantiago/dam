using UnityEngine;

public class MoveCubes : MonoBehaviour
{
    public Transform[] cubes;           // Asigna los 4 cubos desde el inspector
    public float moveDistance = 2f;     // Distancia que se moverán los cubos
    public float moveSpeed = 2f;        // Velocidad de movimiento
    public bool randomColors = true;    // Si es true, colores aleatorios; si no, predefinidos

    private Vector3[] startPositions;

    void Start()
    {
        if (cubes == null || cubes.Length == 0)
        {
            Debug.LogError("Asigna los cubos en el inspector.");
            return;
        }

        startPositions = new Vector3[cubes.Length];

        // Colores predefinidos si no se usan aleatorios
        Color[] predefinedColors = new Color[]
        {
            Color.red,
            Color.green,
            Color.blue,
            Color.yellow
        };

        for (int i = 0; i < cubes.Length; i++)
        {
            startPositions[i] = cubes[i].position;

            // Obtener el Renderer del cubo
            Renderer renderer = cubes[i].GetComponent<Renderer>();
            if (renderer != null)
            {
                if (randomColors)
                    // Asignar color si randomColors está activado
                    renderer.material.color = new Color(Random.value, Random.value, Random.value);
                else
                    // Asignar color predefinido
                    renderer.material.color = predefinedColors[i % predefinedColors.Length];
            }
        }
    }

    void Update()
    {
        for (int i = 0; i < cubes.Length; i++)
        {
            float offset = Mathf.Sin(Time.time * moveSpeed);

            // Cubos impares primero derecha
            if (i % 2)
                cubes[i].position = startPositions[i] + new Vector3(offset * moveDistance, 0, 0);
            // Cubos pares primero izquierda
            else
                cubes[i].position = startPositions[i] + new Vector3(-offset * moveDistance, 0, 0);
        }
    }
}
