package domain.asset.api

import com.side.tiggle.domain.asset.dto.AssetDto
import com.side.tiggle.domain.asset.dto.resp.AssetRespDto
import com.side.tiggle.domain.asset.service.AssetService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/asset")
class AssetApiController(
    private val assetService: AssetService
){

    @PostMapping
    fun createAsset(@RequestBody assetDto: AssetDto): ResponseEntity<AssetRespDto> {
        print("kotlin asset post api test")
        return ResponseEntity.ok(AssetRespDto.fromEntity(assetService.createAsset(assetDto)))
    }

    @GetMapping
    fun getAsset(@PathVariable("id") assetId: Long): ResponseEntity<AssetRespDto> {
        return ResponseEntity.ok(AssetRespDto.fromEntity(assetService.getAsset(assetId)))
    }

    @GetMapping("/all")
    fun getAllAsset(): ResponseEntity<List<AssetRespDto>> {
        val allAssets = assetService.getAllAsset()
        val assetRespDtos = allAssets.map { AssetRespDto.fromEntity(it) }
        return ResponseEntity.ok(assetRespDtos)
    }
}