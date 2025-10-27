using UnityEngine;

public class SpringHandler : MonoBehaviour
{
    private Vector3 initialScale;
    private Vector3 compressedScale;

    [Header("Test")]
    [SerializeField] private float compressionFactor = 0.5f;
    [SerializeField] private float springSpeed = 5f;

    void Start()
    {
        initialScale = transform.localScale;
        compressedScale = new Vector3(initialScale.x, initialScale.y, initialScale.z);
    }

    void Update()
    {
        bool isSpacePressed = Input.GetKeyDown(KeyCode.Space);
        Debug.Log("Test");
        // Debug logging
        if (isSpacePressed)
        {
            Debug.Log("Space key detected!");
        }

        Vector3 targetScale = isSpacePressed ? compressedScale : initialScale;
        transform.localScale = Vector3.Lerp(transform.localScale, targetScale, springSpeed * Time.deltaTime);
    }
}
